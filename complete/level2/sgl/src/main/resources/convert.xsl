<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:template match="entries">
        <entries>
            <xsl:apply-templates/>
        </entries>
    </xsl:template>

    <xsl:template match="entry">
        <entry>
            <xsl:apply-templates/>
        </entry>
    </xsl:template>

    <xsl:template match="field">
        <xsl:attribute name="field">
            <xsl:apply-templates/>
        </xsl:attribute>
    </xsl:template>

</xsl:stylesheet>