package com.avi.minio.modal;

import java.time.ZonedDateTime;

public class FileInfo {
    private String name;
    private long size;
    private ZonedDateTime lastModified;

    public FileInfo(String name, long size, ZonedDateTime lastModified) {
        this.name = name;
        this.size = size;
        this.lastModified = lastModified;
    }

    // Getters and setters...

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
    }
}
