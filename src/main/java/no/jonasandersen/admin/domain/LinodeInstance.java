package no.jonasandersen.admin.domain;

import java.util.List;

public record LinodeInstance(Long id, LinodeId linodeId, List<String> ip, String status, String label,
                             List<String> tags, List<String> volumeNames, LinodeSpecs specs) {

  public String prettyPrintTags() {
    if (tags.isEmpty()) {
      return "-";
    }
    return String.join(", ", tags);
  }

  public String prettyPrintVolumeNames() {
    if (volumeNames.isEmpty()) {
      return "-";
    }
    return String.join(", ", volumeNames);
  }

  public String prettyPrintLabel() {
    return label.substring(0, 1).toUpperCase() + label.substring(1).toLowerCase();
  }

  public String prettyPrintStatus() {
    return status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
  }

  public String prettyPrintIp() {
    return String.join(", ", ip);
  }

  public String prettyPrintSpecs() {
    return specs.memory() + " MB";
  }

  public String owner() {
    return tags.stream()
        .filter(tag -> tag.startsWith("owner:"))
        .map(tag -> tag.substring(6))
        .findFirst()
        .orElse("unknown");
  }

  public static LinodeInstance createNull() {
    return new LinodeInstance(null, new LinodeId(0L), List.of(), "", "", List.of(), List.of(),
        new LinodeSpecs(0));
  }

  public static LinodeInstance createNull(String label, List<String> ip) {
    return new LinodeInstance(null, LinodeId.createNull(), List.copyOf(ip), "", label, List.of(), List.of(),
        new LinodeSpecs(0));
  }

}