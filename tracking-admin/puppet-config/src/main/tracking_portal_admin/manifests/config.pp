class ${puppet-config.modulename}__v${puppet-config.version}::config {

  file { 'systemd_unit_${project.parent.artifactId}':
    ensure   => present,
    path     => '${rpm.systemd.dir}/${project.parent.artifactId}.service',
    content  => template('${puppet-config.modulename}__v${puppet-config.version}/systemd-unit.erb'),
    mode     => '0644',
    owner    => 'root',
    group    => 'root'
  }

  exec { 'systemd daemon-reload ${project.parent.artifactId}':
    command     => 'systemctl daemon-reload',
    path        => ['/usr/bin', '/usr/sbin', '/bin', '/sbin'],
    subscribe   => File['systemd_unit_${project.parent.artifactId}'],
    refreshonly => true,
    notify  => Service['${project.parent.artifactId}'],
  }

  file { 'systemd_run_${project.parent.artifactId}':
    ensure   => present,
    path     => '${rpm.run.script.dir}/${project.parent.artifactId}.sh',
    content  => template('${puppet-config.modulename}__v${puppet-config.version}/systemd-run.erb'),
    mode     => '0774',
    owner    => '${rpm.run.user}',
    group    => '${rpm.run.user}',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-application.conf':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/application.conf.erb'),
    path    => '${rpm.config.dir}/${project.parent.artifactId}/application.conf',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-logback.xml':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/logback.xml.erb'),
    path    => '${rpm.config.log.conf.dir}/${project.parent.artifactId}/logback.xml',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-speed4j.properties':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/speed4j.properties.erb'),
    path    => '${rpm.config.log.conf.dir}/${project.parent.artifactId}/speed4j.properties',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-jdbc.properties':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/jdbc.properties.erb'),
    path    => '${rpm.config.dir}/${project.parent.artifactId}/jdbc.properties',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-data-export.properties':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/data-export.properties.erb'),
    path    => '${rpm.config.dir}/${project.parent.artifactId}/data-export.properties',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-session.properties':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/session.properties.erb'),
    path    => '${rpm.config.dir}/${project.parent.artifactId}/session.properties',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-application.properties':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/application.properties.erb'),
    path    => '${rpm.config.dir}/${project.parent.artifactId}/application.properties',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-kafka.properties':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/kafka.properties.erb'),
    path    => '${rpm.config.dir}/${project.parent.artifactId}/kafka.properties',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-external-services.properties':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/external-services.properties.erb'),
    path    => '${rpm.config.dir}/${project.parent.artifactId}/external-services.properties',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-activeDirectory.json':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/activeDirectory.json.erb'),
    path    => '${rpm.config.dir}/${project.parent.artifactId}/activeDirectory.json',
    notify  => Service['${project.parent.artifactId}'],
  }

  file {'${project.parent.artifactId}-knownSoftwareNames.json':
    ensure  => present,
    content => template('${puppet-config.modulename}__v${puppet-config.version}/knownSoftwareNames.json.erb'),
    path    => '${rpm.config.dir}/${project.parent.artifactId}/knownSoftwareNames.json',
    notify  => Service['${project.parent.artifactId}'],
  }


}
