param(
    [switch]$Relative
)
$root = (Resolve-Path .).Path
$files = Get-ChildItem -LiteralPath $root -Recurse -Force -ErrorAction SilentlyContinue | Where-Object { $_.Name -eq ".DS_Store" }
if ($Relative) {
    $paths = $files | ForEach-Object { $_.FullName.Substring($root.Length).TrimStart('\','/').Replace('\','/') }
} else {
    $paths = $files | ForEach-Object { $_.FullName }
}
$paths | Sort-Object
"Total: {0}" -f ($paths.Count)
