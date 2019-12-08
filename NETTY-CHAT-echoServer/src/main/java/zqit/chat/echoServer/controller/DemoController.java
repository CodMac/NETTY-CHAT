package zqit.chat.echoServer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

	@GetMapping("/restfulTest/{something}")
	public String restfulTest(@PathVariable("something") String something){
		return something;
	}
	
}
