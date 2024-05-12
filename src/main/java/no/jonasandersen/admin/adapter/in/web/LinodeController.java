package no.jonasandersen.admin.adapter.in.web;

import de.tschuehly.spring.viewcomponent.jte.ViewContext;
import java.util.Optional;
import no.jonasandersen.admin.adapter.in.web.layout.MainLayoutViewComponent;
import no.jonasandersen.admin.adapter.in.web.linode.LinodeDetailViewComponent;
import no.jonasandersen.admin.adapter.in.web.linode.LinodeViewComponent;
import no.jonasandersen.admin.adapter.in.web.linode.create.CreateFormComponent;
import no.jonasandersen.admin.application.LinodeService;
import no.jonasandersen.admin.application.ServerGenerator;
import no.jonasandersen.admin.application.ServerGenerator.ServerType;
import no.jonasandersen.admin.core.domain.InstanceNotFound;
import no.jonasandersen.admin.core.domain.LinodeId;
import no.jonasandersen.admin.core.domain.LinodeInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/linode")
public class LinodeController {

  private static final Logger log = LoggerFactory.getLogger(LinodeController.class);
  private final MainLayoutViewComponent mainLayoutViewComponent;
  private final LinodeViewComponent linodeViewComponent;
  private final LinodeDetailViewComponent linodeDetailViewComponent;

  private final CreateFormComponent createFormComponent;
  private final LinodeService linodeService;
  private final ServerGenerator serverGenerator;

  public LinodeController(MainLayoutViewComponent mainLayoutViewComponent,
      LinodeViewComponent linodeViewComponent,
      LinodeDetailViewComponent linodeDetailViewComponent,
      CreateFormComponent createFormComponent,
      LinodeService linodeService, ServerGenerator serverGenerator) {
    this.mainLayoutViewComponent = mainLayoutViewComponent;
    this.linodeViewComponent = linodeViewComponent;
    this.linodeDetailViewComponent = linodeDetailViewComponent;
    this.createFormComponent = createFormComponent;
    this.linodeService = linodeService;
    this.serverGenerator = serverGenerator;
  }

  @GetMapping
  ViewContext linode() {
    return mainLayoutViewComponent.render("Linode", linodeViewComponent.render());
  }

  @GetMapping("/{linodeId}")
  ViewContext getInstance(@PathVariable Long linodeId) {
    Optional<LinodeInstance> instance = linodeService.findInstanceById(new LinodeId(linodeId));

    return mainLayoutViewComponent.render("Linode Detail - " + linodeId,
        linodeDetailViewComponent.render(instance.orElse(null)));
  }

  @ExceptionHandler(InstanceNotFound.class)
  public String handleInstanceNotFound() {
    return "redirect:/linode";
  }

  @GetMapping("/create")
  ViewContext create() {
    return mainLayoutViewComponent.render("Create Linode", createFormComponent.render());
  }

  @PostMapping("/create")
  String createResponse(@RequestParam ServerType serverType) {
    log.info("Creating server of type {}", serverType);

    serverGenerator.generate(serverType);
    return "redirect:/linode";
  }

}
