package com.itavenues.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "online_status")
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStatusEntity {

  @Id
  @Column(name = "user_id")
  private String id;

  @Column(name = "online")
  private boolean online;
}
