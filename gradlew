#!/usr/bin/env sh

DIR="$(cd "$(dirname "$0")" && pwd)"
GRADLE_CMD="$DIR/gradle/wrapper/gradle-wrapper.jar"

java -classpath "$GRADLE_CMD" org.gradle.wrapper.GradleWrapperMain "$@"
