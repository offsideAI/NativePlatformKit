# NativePlatformKit Test Harness — build/run convenience targets.
#
# The macOS build uses CocoaPods, which on this machine lives under chruby Ruby 3.4.4 (not the
# system Ruby). These targets activate it automatically so `pod install` resolves, regardless of
# the calling shell. Flutter is expected on PATH (sourced from ~/.zprofile / ~/.zshrc).

SHELL := /bin/bash
RUBY_VERSION ?= ruby-3.4.4
CHRUBY := /opt/homebrew/opt/chruby/share/chruby/chruby.sh
# Activate chruby + the pinned Ruby for any recipe that may invoke CocoaPods.
ACTIVATE_RUBY := if [ -f "$(CHRUBY)" ]; then source "$(CHRUBY)" && chruby $(RUBY_VERSION); fi

.DEFAULT_GOAL := help

.PHONY: help
help: ## Show this help.
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | \
		awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-14s\033[0m %s\n", $$1, $$2}'

.PHONY: get
get: ## Fetch Dart/Flutter dependencies.
	flutter pub get

.PHONY: analyze
analyze: ## Static analysis (must be clean).
	flutter analyze

.PHONY: test
test: ## Run unit tests.
	flutter test

.PHONY: run
run: ## Run the harness on macOS (debug).
	@$(ACTIVATE_RUBY); flutter run -d macos

.PHONY: build
build: ## Build the macOS app (debug).
	@$(ACTIVATE_RUBY); flutter build macos --debug

.PHONY: release
release: ## Build the macOS app (release).
	@$(ACTIVATE_RUBY); flutter build macos --release

.PHONY: catalog
catalog: ## Regenerate assets/catalog.json from the playground fragment inventory.
	python3 tool/generate_catalog.py

.PHONY: verify
verify: analyze test ## Analyze + test (CI gate).

.PHONY: clean
clean: ## Remove Flutter build artifacts.
	flutter clean
