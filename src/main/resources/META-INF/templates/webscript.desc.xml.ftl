<webscript>
    <shortname>${shortName}</shortname>
    <description>${description}</description>
    <#list urls as url>
    <url>${url}</url>
    </#list>
    <#if runAs?has_content>
    <authentication runas="${runAs}">${auth}</authentication>
    <#else>
    <authentication>${auth}</authentication>
    </#if>
    <format default="${defaultFormatType}">${formatTypePosition}</format>
    <#if transaction?has_content>
    <transaction>${transaction}</transaction>
    </#if>
</webscript>