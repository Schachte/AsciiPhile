package com.schachte.asciiphile.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class File {
  @Id
  @Column(name = "file")
  private int file;

  @Column(name = "username")
  private String username;

  @Column(name = "name")
  private String name;

  @Column(name = "extension")
  private String extension;

  @Column(name = "directory")
  private String directory;
}
