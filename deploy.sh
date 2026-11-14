#!/usr/bin/env bash
# Builds the WAR (optional), copies it to Tomcat's webapps and restarts Tomcat.
# Usage: ./deploy.sh [--build]
set -euo pipefail

export JAVA_HOME="${JAVA_HOME:-$HOME/.sdkman/candidates/java/21.0.11-oracle}"
export CATALINA_HOME="${CATALINA_HOME:-$HOME/apache-tomcat-11.0.29}"
export PATH="$JAVA_HOME/bin:$PATH"

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
WAR_NAME="pizzeria-palmetto.war"
WAR_SRC="$PROJECT_DIR/target/$WAR_NAME"
WEBAPPS="$CATALINA_HOME/webapps"

if [[ "${1:-}" == "--build" ]]; then
    (cd "$PROJECT_DIR" && mvn -q -DskipTests clean package)
fi

[[ -f "$WAR_SRC" ]] || { echo "WAR not found: $WAR_SRC (run with --build)"; exit 1; }

echo "Stopping Tomcat..."
"$CATALINA_HOME/bin/shutdown.sh" >/dev/null 2>&1 || true
for _ in {1..20}; do
    pgrep -f "catalina.home=$CATALINA_HOME" >/dev/null || break
    sleep 1
done

echo "Deploying $WAR_NAME..."
rm -rf "$WEBAPPS/${WAR_NAME%.war}" "$WEBAPPS/$WAR_NAME"
cp "$WAR_SRC" "$WEBAPPS/$WAR_NAME"

echo "Starting Tomcat..."
"$CATALINA_HOME/bin/startup.sh"

echo "Done. Try: http://localhost:8080/${WAR_NAME%.war}/v1/orders"
echo "Logs: tail -f $CATALINA_HOME/logs/catalina.out"
