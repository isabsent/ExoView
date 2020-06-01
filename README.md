# ExoView
[ExoVideoView](https://github.com/JarvanMo/ExoVideoView) adaptation

## Gradle:

    dependencies {
      implementation 'com.isabsent.exoview:exoview:0.0.5'
    }
    
or in the case of some conflicts in compiling time between androidx- and support- libraries like a

    "Program type already present: android.support.v4.xxx"
exclude androidx.media group

    implementation ('com.isabsent.exoview:exoview:0.0.5'){
        exclude group: 'androidx.media'
    }

## Maven:

    <dependency>
      <groupId>com.github.isabsent</groupId>
      <artifactId>exoview</artifactId>
      <version>0.0.5</version>
      <type>pom</type>
    </dependency>
