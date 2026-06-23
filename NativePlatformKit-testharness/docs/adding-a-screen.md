# Adding or fixing a catalog screen

The Flow Runner is driven by [`assets/catalog.json`](../assets/catalog.json) — a list of categories,
each with screens. Each screen has an id, title, human navigation instructions, a description, and a
screenshot path.

```json
{
  "appPackage": "ai.offside.mobile.android.testlabs",
  "launchActivity": "ai.offside.mobile.android.helper.testlabs.nav.TestlabsMainActivity",
  "categories": [
    {
      "id": "buttons",
      "title": "Buttons",
      "screens": [
        {
          "id": "buttons.primary",
          "title": "Primary Buttons",
          "navInstructions": ["From the Home grid, open ‘Buttons’.", "Tap ‘Primary Buttons’."],
          "description": "Primary button styles.",
          "screenshot": "buttons/primary-buttons.png"
        }
      ]
    }
  ]
}
```

## Regenerate from the playground

The catalog is generated from the playground's `*Fragment.kt` inventory:

```bash
make catalog          # → tool/generate_catalog.py rewrites assets/catalog.json
```

Re-run this whenever the playground gains/loses screens. The generator groups by package directory
(see the `CAT` map in `tool/generate_catalog.py`) and writes structural instructions
("From the Home grid, open ‘<Category>’ → Navigate to ‘<Title>’").

## Curating instructions

The generated `navInstructions` are a structural baseline. To make them precise, edit
`assets/catalog.json` (or the `CAT` labels in the generator) so the steps match the **real** home
grid labels (e.g. *Theme, Typography, Buttons, Modals, Slider, Controls, Info Boxes, Amount Input
Field, Card Tile Component, Stepper, WebViews, Account Tile, …*).

## Rules (enforced by `CatalogService`)

- `id` must be unique across the whole catalog (convention: `<category-slug>.<screen-slug>`).
- `navInstructions` must be non-empty.
- `screenshot` must be non-empty (convention: `<category-slug>/<screen-slug>.png`); this is the path
  used under both `runs/<id>/` and `latest/`.

## Verify

```bash
make analyze
make test     # includes a catalog validity + coverage (>=70 screens) check
```

The Flow Runner picks up changes on next launch (the catalog is a bundled asset, so re-run the app).
