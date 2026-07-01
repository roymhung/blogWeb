package vn.BlogWeb.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.BlogWeb.model.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

	@NotBlank(message = "name không được để trống")
	private String name;

	@NotBlank(message = "address không được để trống")
	private String address;

	private Role role;
}
