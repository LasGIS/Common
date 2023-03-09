class ${puppet-config.modulename}__v${puppet-config.version}::install {
    if 'SNAPSHOT' in '${project.version}' {
        package {'${project.parent.artifactId}.assembly':
            ensure  => '${project.version}${rpm.build.timestamp}',
        }
    } else {
        package {'${project.parent.artifactId}.assembly':
            ensure  => '${project.version}-1',
        }
    }
}
