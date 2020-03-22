package requests;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailRequest implements Serializable
{
	private String to;

	private String from;

	private String subject;

	private String body;

	private List<AttachmentRequest> attachments = new ArrayList<>();
}