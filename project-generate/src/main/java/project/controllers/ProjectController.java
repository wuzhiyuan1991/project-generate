package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.service.IProjectGeneratorService;

@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	IProjectGeneratorService projectGeneratorServiceImpl;

	@RequestMapping(path = "/create/{artifactid}.zip", method = RequestMethod.GET)
	public byte[] create(@RequestParam(required = true) String groupid,
			@PathVariable("artifactid") String artifactid, @RequestParam(required = true) String pkg,
			@RequestParam(required = true) String port, @RequestParam(required = true) String dubboport,
			@RequestParam(required = true) String restport, @RequestParam(required = true) String prjectid)
			throws Exception {
		//return "create " + artifactid + " succ ==> " + path;
		return projectGeneratorServiceImpl.run(groupid, artifactid, pkg, port, dubboport, restport, prjectid);
	}
}
