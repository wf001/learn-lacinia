include .env
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
# Development
##################
serve-db:
	docker compose up db




##################
# NOTICE: The command below this is a development-only command and is not intended for general use.
##################


##################
# Test
##################
api-test:
	bb ./backend/tests/main.clj

###################
## Deployment
###################
commit-all:
	docker commit sakila-nginx ${IMAGE_REPO_ENDPOINT}/${IMAGE_REPO_PREFIX}/${IMAGE_REPO_NGINX}:${IMAGE_TAG}
	docker commit sakila-clj-api ${IMAGE_REPO_ENDPOINT}/${IMAGE_REPO_PREFIX}/${IMAGE_REPO_CLJ_API}:${IMAGE_TAG}
	docker commit sakila-db ${IMAGE_REPO_ENDPOINT}/${IMAGE_REPO_PREFIX}/${IMAGE_REPO_DB}:${IMAGE_TAG}

push-all:
	docker push ${IMAGE_REPO_ENDPOINT}/${IMAGE_REPO_PREFIX}/${IMAGE_REPO_NGINX}:${IMAGE_TAG}
	docker push ${IMAGE_REPO_ENDPOINT}/${IMAGE_REPO_PREFIX}/${IMAGE_REPO_CLJ_API}:${IMAGE_TAG}
	docker push ${IMAGE_REPO_ENDPOINT}/${IMAGE_REPO_PREFIX}/${IMAGE_REPO_DB}:${IMAGE_TAG}

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

