class ${puppet-config.modulename}__v${puppet-config.version}::service {

	service {'${project.parent.artifactId}':
		ensure => running,
		enable => true,
	}

}
