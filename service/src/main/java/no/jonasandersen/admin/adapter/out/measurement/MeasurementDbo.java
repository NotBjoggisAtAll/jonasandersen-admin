package no.jonasandersen.admin.adapter.out.measurement;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class MeasurementDbo {

  @Id
  @GeneratedValue
  private Long id;

  private String temperature;

  private int humidity;

  private LocalDateTime timestamp;

  @PrePersist
  public void prePersist() {
    if (timestamp == null) {
      timestamp = LocalDateTime.now(ZoneId.of("UTC"));
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public int getHumidity() {
    return humidity;
  }

  public void setHumidity(int humidity) {
    this.humidity = humidity;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "MeasurementDbo{" +
        "id=" + id +
        ", temperature='" + temperature + '\'' +
        ", humidity=" + humidity +
        ", timestamp=" + timestamp +
        '}';
  }
}