package requests;

import lombok.Setter;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Setter
public class AttachmentRequest implements Serializable
{
	private String filename;

	private String content;
}