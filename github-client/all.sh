#!/bin/bash

projects=("acme" "acme-api" "acme-client" "bobbinet" "bobbinet-api" "butter" "certTester" "clearingservices" "clearingservices1" "common" "dcs-api" "dependencies" "dn_automation" "dn_clearingservices" "dn_old_svn_repo" "dn_playbooks" "e2e" "filefeeder" "formatter" "happyTestinatorServer" "jauth" "jbooker" "jcs" "jfdi" "jim" "jimHelp" "jls" "jonoff" "jps" "jrectool" "jtomas" "jus" "kafka" "library" "ma" "NewJoiner" "pairdemo" "performance-tests" "play-scalajs-showcase" "pusher" "reports-api" "reprobate" "sbt-work" "scratchpad" "screensaverPreventer" "serverProfile" "smart-releases" "sqldeveloperConnections" "template" "tools" "treelog" "watchman" "watchman-api")
for p in ${projects[@]}; do
    f="github_$p"
    if [ ! -f $f ]; then
        ./g.sh $p
    fi
done
