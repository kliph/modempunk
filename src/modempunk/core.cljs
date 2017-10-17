(ns modempunk.core
  (:require [reagent.core :as r]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.core.matrix :as mat :refer [M32 M44]]
            [thi.ng.geom.webgl.animator :refer [animate]]
            [thi.ng.geom.svg.core :as svg]
            [goog.dom]))

(def by-id goog.dom.getElement)

(defn app-container []
  (svg/svg
   {:width 400 :height 300 :viewbox "0 0 400 300"}
   (svg/group
    {:stroke-width 0.1
     :fill "fff"
     :stroke "fff"}
    (svg/circle [100 100] 100))))

(r/render-component [app-container] (by-id "app"))
