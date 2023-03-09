class ${puppet-config.modulename}__v${puppet-config.version}::application {

  contain ${puppet-config.modulename}__v${puppet-config.version}::install
  contain ${puppet-config.modulename}__v${puppet-config.version}::config
  contain ${puppet-config.modulename}__v${puppet-config.version}::service

  Class['${puppet-config.modulename}__v${puppet-config.version}::install']
  -> Class['${puppet-config.modulename}__v${puppet-config.version}::config']
  ~> Class['${puppet-config.modulename}__v${puppet-config.version}::service']

}
