

COMPILE_OPTIONS = -d bin/ -cp bin/

all : Everything


Everything : src/com/**/**/*.java
	javac $(COMPILE_OPTIONS) src/com/**/**/*.java

