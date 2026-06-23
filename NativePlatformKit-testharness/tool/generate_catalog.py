#!/usr/bin/env python3
"""Generate assets/catalog.json from the playground's fragment inventory.

Enumerates every *Fragment.kt under the TestLabs playground, groups by package directory, and emits
a catalog of categories -> screens with structural human navigation instructions. Re-run after the
playground's screens change:  python3 tool/generate_catalog.py
"""
import os
import re
import json

HERE = os.path.dirname(os.path.abspath(__file__))
HARNESS = os.path.dirname(HERE)
ROOT = os.path.normpath(os.path.join(
    HARNESS, "..", "NativePlatformKit-TestLabs-Android-Playground",
    "app/src/main/kotlin/ai/offside/mobile/android/helper/testlabs"))
OUT = os.path.join(HARNESS, "assets", "catalog.json")

# Category label per package directory (relative to the testlabs root).
CAT = {
    "color/generator": "Color Generator", "nav/badge": "Badges", "nav/buttons": "Takeover Buttons",
    "nav/carousel": "Carousel", "nav/controls": "Controls", "nav/dropdown": "Dropdown",
    "nav/icons/decorative": "Icons", "nav/icons/informative": "Icons", "nav/inputfield": "Input Fields",
    "nav/listitem": "List Items", "nav/modals": "Modals", "nav/modals/bottomsheet": "Bottom Sheets",
    "nav/review": "Review", "nav/stepper": "Stepper", "nav/tile": "Tiles", "nav/tile/account": "Tiles · Account",
    "nav/tile/deposit": "Tiles · Deposit", "nav/tile/online_payments": "Tiles · Online Payments",
    "nav/tile/transfer": "Tiles · Transfer", "nav/webviews": "WebViews", "nav": "Components",
}
EXCLUDE = {"TestlabsMainActivity", "TestUIRedesignActivity"}


def slug(s):
    return re.sub(r"[^a-z0-9]+", "-", s.lower()).strip("-")


def humanize(cls):
    s = cls
    for junk in ["Fragment", "TestUIRedesign", "TestUiRedesign", "TestRedesign",
                 "TestUIResign", "Test", "UIRedesign", "Redesign", "UIResign"]:
        s = s.replace(junk, "")
    s = re.sub(r"(?<=[a-z])(?=[A-Z])", " ", s)
    s = re.sub(r"(?<=[A-Z])(?=[A-Z][a-z])", " ", s)
    return re.sub(r"\s+", " ", s).strip() or cls.replace("Fragment", "")


def main():
    cats = {}
    for dirpath, _, files in os.walk(ROOT):
        rel = os.path.relpath(dirpath, ROOT)
        rel = "" if rel == "." else rel
        for f in files:
            if not f.endswith("Fragment.kt"):
                continue
            cls = f[:-3]
            if cls in EXCLUDE:
                continue
            label = CAT.get(rel) or CAT.get(rel.rsplit("/", 1)[0]) or "Components"
            cats.setdefault(label, set()).add((cls, humanize(cls)))

    catalog = {
        "appPackage": "ai.offside.mobile.android.testlabs",
        "launchActivity": "ai.offside.mobile.android.helper.testlabs.nav.TestlabsMainActivity",
        "categories": [],
    }
    total = 0
    for label in sorted(cats):
        cslug = slug(label)
        screens = []
        for cls, title in sorted(cats[label], key=lambda x: x[1]):
            screens.append({
                "id": f"{cslug}.{slug(title)}",
                "title": title,
                "navInstructions": [
                    f"From the Home grid, open the ‘{label}’ section.",
                    f"Navigate to ‘{title}’.",
                ],
                "description": f"{title} demo in the {label} section.",
                "screenshot": f"{cslug}/{slug(title)}.png",
            })
            total += 1
        catalog["categories"].append({"id": cslug, "title": label, "screens": screens})

    with open(OUT, "w") as fh:
        json.dump(catalog, fh, indent=2, ensure_ascii=False)
        fh.write("\n")
    print(f"Wrote {OUT}: {len(catalog['categories'])} categories, {total} screens")


if __name__ == "__main__":
    main()
