package no.jonasandersen.admin.adapter.in.web;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import java.util.List;
import java.util.stream.Collectors;
import no.jonasandersen.admin.adapter.out.websocket.Model;
import no.jonasandersen.admin.core.LinodeVolumeService;
import no.jonasandersen.admin.core.domain.LinodeInstance;
import no.jonasandersen.admin.core.domain.LinodeVolume;
import no.jonasandersen.admin.core.minecraft.MinecraftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hx/linode")
public class LinodeHxController {

  private static final Logger log = LoggerFactory.getLogger(LinodeHxController.class);
  private final LinodeVolumeService linodeVolumeService;
  private final JteHtmlGenerator jteHtmlGenerator;
  private final MinecraftService minecraftService;


  public LinodeHxController(LinodeVolumeService linodeVolumeService,
      JteHtmlGenerator jteHtmlGenerator, MinecraftService minecraftService) {
    this.linodeVolumeService = linodeVolumeService;
    this.jteHtmlGenerator = jteHtmlGenerator;
    this.minecraftService = minecraftService;

  }

  @GetMapping("/instance/create")
  @HxRequest
  String createInstance() {
    log.info("Returning create instance form");
    return jteHtmlGenerator.generateHtml("linode/create-form");
  }

  @GetMapping("/instance/refresh")
  @HxRequest
  String refreshInstance() {

    List<LinodeInstance> instances = minecraftService.getInstances();
    return instances.stream()
        .map(instance -> jteHtmlGenerator.generateHtml("linode/instance", Model.from(instance)))
        .collect(Collectors.joining());
  }

  @GetMapping("/volumes")
  @HxRequest
  String getVolumes() {
//    List<LinodeVolume> volumes = service.getVolumes();
    List<LinodeVolume> volumes = List.of();

    String collect = volumes.stream().map("""
            <li>%s, </li>
            """::formatted)
        .collect(Collectors.joining());

    return """
        <ul>
          %s
        </ul >
        """.formatted(collect);
  }
}
