package org.acme.dto;

import org.acme.enums.Approved;
import org.acme.enums.DisabledOrEnabled;

public class UserListDto {
    public Long userId;
    public Approved approved;
    public DisabledOrEnabled disabledOrEnabled;
}
