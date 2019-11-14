package com.kayukin.timer.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String id;

    private String email;

    private String name;

    private List<Membership> memberships;

    private String profilePicture;

    private String activeWorkspace;

    private String defaultWorkspace;

    private Settings settings;

    private String status;
}
