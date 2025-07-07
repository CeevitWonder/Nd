#!/usr/bin/env sh
##############################################################################
# Gradle start-up script for UN*X
##############################################################################

if [ -z "$APP_HOME" ]; then
  APP_HOME="$(cd "$(dirname "$0")" && pwd)"
fi

GRADLE_HOME="$APP_HOME/gradle/wrapper"
CLASSPATH="$GRADLE_HOME/gradle-wrapper.jar"

exec java -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
