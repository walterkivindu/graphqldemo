package com.example.graphqldemo.models.files;

import java.io.Serializable;

 public record FileData (String name, Long id) implements Serializable  {
}
