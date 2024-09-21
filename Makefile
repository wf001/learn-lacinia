##################
# Setting
##################
check-deps:
	# used to test API
	clj --version
	# used to run docker container
	docker --version
	# used to run docker container
	node -v


##################
# NOTICE: The command below this is a development-only command and is not intended for general use.
##################


##################
# Test
##################
api-test:
	bb ./backend/tests/main.clj


###################
## Document
###################
serve-gql-doc:
	spectaql ./docs/graphql/config.yml -t docs/graphql -D

serve-rest-doc:
	python -m http.server 8000 -d docs/rest

REPO_URL = https://github.com/wf001/learn-lacinia

show-compare-tags:
	@tags=$$(git tag); \
	num_tags=$$(echo "$$tags" | wc -l); \
	for i in $$(seq 2 $$num_tags); do \
		tag_old=$$(echo "$$tags" | sed -n $$((i-1))p); \
		tag_new=$$(echo "$$tags" | sed -n $$i"p"); \
		echo "- [$$tag_new](https://github.com/wf001/learn-lacinia/tree/$$tag_new)"; \
		echo "  - message"; \
		echo "  - [diff]($(REPO_URL)/compare/$$tag_old...$$tag_new)"; \
	done

