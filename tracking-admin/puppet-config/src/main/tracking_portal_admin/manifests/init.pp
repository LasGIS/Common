class ${puppet-config.modulename}__v${puppet-config.version} {

    contain post_base
    contain ${puppet-config.modulename}__v${puppet-config.version}::application

    Class['post_base'] -> Class['${puppet-config.modulename}__v${puppet-config.version}::application']
}

