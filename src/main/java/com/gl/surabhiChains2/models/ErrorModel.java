package com.gl.surabhiChains2.models;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ErrorModel
{
	private String errorMessage;
	private Integer errorCode;
	private LocalDateTime timeStamp;
}
