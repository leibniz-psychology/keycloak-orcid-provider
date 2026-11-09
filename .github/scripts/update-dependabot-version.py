#!/usr/bin/env python3

import os
import re
import subprocess
from pathlib import Path


def project_version(pom: str) -> tuple[str, str]:
    match = re.search(r"(?m)^    <version>([^<]+)</version>$", pom)
    if match is None:
        raise ValueError("Could not find the project version in pom.xml")
    return match.group(1), match.group(0)


def keycloak_version(pom: str) -> str:
    match = re.search(r"(?m)^        <keycloak\.version>([^<]+)</keycloak\.version>\s*$", pom)
    if match is None:
        raise ValueError("Could not find keycloak.version in pom.xml")
    return match.group(1)


base_sha = os.environ["BASE_SHA"]
update_type = os.environ["UPDATE_TYPE"]
dependency_names = os.environ.get("DEPENDENCY_NAMES", "")
names = [
    name.strip()
    for name in re.split(r"[,\n]", dependency_names)
    if name.strip()
]
if not names:
    raise ValueError("Dependabot did not report the updated dependency names")

base_pom = subprocess.check_output(
    ["git", "show", f"{base_sha}:pom.xml"], text=True
)
pom_path = Path("pom.xml")
pom = pom_path.read_text()
base_keycloak = keycloak_version(base_pom)
current_keycloak = keycloak_version(pom)
base_project, _ = project_version(base_pom)
_, current_project_match = project_version(pom)

version_match = re.fullmatch(r"(\d+)\.(\d+)\.(\d+)-(.+)", base_project)
if version_match is None:
    raise ValueError(f"Unexpected base project version: {base_project}")

major, minor, patch = map(int, version_match.groups()[:3])
keycloak_only = (
    current_keycloak != base_keycloak
    and all(name.startswith("org.keycloak:") for name in names)
)

if not keycloak_only:
    if update_type == "version-update:semver-patch":
        patch += 1
    elif update_type == "version-update:semver-minor":
        minor += 1
        patch = 0
    else:
        raise ValueError(f"Unsupported non-major update type: {update_type}")

new_version = f"{major}.{minor}.{patch}-{current_keycloak}"
pom = pom.replace(current_project_match, f"    <version>{new_version}</version>", 1)
pom_path.write_text(pom)
