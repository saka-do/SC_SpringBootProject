package com.gl.surabhiChains2.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo
{
	private String message;
	private Integer userId;
	private String userName;
	private String email;
	private String role;
}
