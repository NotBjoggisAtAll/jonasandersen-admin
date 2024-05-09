package no.jonasandersen.admin.core.domain;

public record LinodeId(Long id) {

  public static LinodeId from(Long linodeId) {
    return new LinodeId(linodeId);
  }

  public static LinodeId createNull() {
    return new LinodeId(0L);
  }
}
