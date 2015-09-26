

COMPILE_OPTIONS = -d Anochat/bin/ -cp Anochat/bin/

all : Everything


Everything : Anochat/src/com/**/**/*.java
	javac $(COMPILE_OPTIONS) Anochat/src/com/**/**/*.java
	javac $(COMPILE_OPTIONS) Anochat/src/com/**/*.java
