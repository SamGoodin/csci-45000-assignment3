JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		Assignment3/server/Server.java \
		Assignment3/client/CartRem.java \
		Assignment3/client/Item.java \
		Assignment3/client/LoginRem.java \
		Assignment3/client/LoginRemImpl.java \
		Assignment3/client/MainClient.java \
		Assignment3/client/ShoppingCart.java


default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class