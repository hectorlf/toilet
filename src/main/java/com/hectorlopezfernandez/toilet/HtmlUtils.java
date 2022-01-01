package com.hectorlopezfernandez.toilet;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.unbescape.html.HtmlEscape;


public final class HtmlUtils {

	private HtmlUtils() {
		// non-instantiable
	}

	private static Pattern NEW_LINES = Pattern.compile("\\R");
	private static Pattern CONTIGUOUS_PARAGRAPHS = Pattern.compile("</p><p>");
	private static Safelist FEED_SAFELIST = Safelist.basicWithImages();
	private static Safelist INDEX_SAFELIST = Safelist.none();

	public static String preprocessForFeeds(String html) {
		if (html == null || html.isEmpty()) return html;

		return Jsoup.clean(html, FEED_SAFELIST);
	}

	public static String preprocessForSearchIndex(String html) {
		if (html == null || html.isEmpty()) return html;
		
		System.out.println(html);
		String processedContent = NEW_LINES.matcher(html).replaceAll(" ");
		System.out.println(processedContent);
		processedContent = CONTIGUOUS_PARAGRAPHS.matcher(processedContent).replaceAll("</p> <p>");
		System.out.println(processedContent);
		processedContent = Jsoup.clean(processedContent, INDEX_SAFELIST);
		System.out.println(processedContent);
		return HtmlEscape.unescapeHtml(processedContent);
	}

}
