package ds.prodigy.apt;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

import static javax.tools.Diagnostic.Kind.*;

final class Logger {

	private javax.annotation.processing.Messager messager;


	void init(ProcessingEnvironment processingEnvironment) {
		messager = processingEnvironment.getMessager();
	}


	void note(Element e, String msg, Object... args) {
		messager.printMessage(NOTE, String.format(msg, args), e);
	}


	void note(String msg, Object... args) {
		messager.printMessage(NOTE, String.format(msg, args));
	}


	void warn(Element e, String msg, Object... args) {
		messager.printMessage(WARNING, String.format(msg, args), e);
	}


	void error(Element e, String msg, Object... args) {
		messager.printMessage(ERROR, String.format(msg, args), e);
	}


	void error(final String msg, final String args) {
		messager.printMessage(ERROR, String.format(msg, args));
	}


}
