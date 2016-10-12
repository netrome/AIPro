(TeX-add-style-hook
 "report"
 (lambda ()
   (TeX-add-to-alist 'LaTeX-provided-package-options
                     '(("inputenc" "utf8") ("parskip" "parfill") ("geometry" "a4paper" "total={6in, 10in}")))
   (TeX-run-style-hooks
    "latex2e"
    "article"
    "art10"
    "inputenc"
    "parskip"
    "geometry"))
 :latex)

