.PHONY: format
format:
	cljstyle check

.PHONY: lint
lint:
	clj-kondo --fail-level error --lint src

.PHONY: static-check
static-check: format lint

.PHONY: test
test:
	bin/kaocha --fail-fast


.PHONY: clean
clean:
	rm -fr target/

.PHONY: build
build: clean
	clojure -T:build uber

start-clj:
	make build
	java -jar target/sakilaapi.jar
