# ExoView
[ExoVideoView](https://github.com/JarvanMo/ExoVideoView) adaptation

## Gradle:

    dependencies {
      implementation 'com.isabsent.exoview:exoview:0.0.2'
    }
    
or in the case of some conflicts between androidx- and support- libraries:
    
    implementation ('com.isabsent.exoview:exoview:0.0.2'){
        exclude group: 'androidx.media'
    }

## Maven:

    <dependency>
      <groupId>com.github.isabsent</groupId>
      <artifactId>exoview</artifactId>
      <version>0.0.2</version>
      <type>pom</type>
    </dependency>
