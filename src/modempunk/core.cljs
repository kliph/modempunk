(ns modempunk.core
  (:require [reagent.core :as r]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.core.matrix :as mat :refer [M32 M44]]
            [thi.ng.geom.webgl.animator :refer [animate]]
            [thi.ng.geom.svg.core :as svg]
            [goog.dom]))

(def by-id goog.dom.getElement)

(def state (r/atom {:ship {:position [100 100]
                           :heading 0}}))

(defn background []
  (svg/group
   {:width "100%"
    :height "100%"
    :fill "fff"}
   (svg/rect [0 0] "100%" "100%")))

(defn ship [{:keys [position heading] :as ship-state}]
  (let [ship-radius 10
        heading-pos [(first position)
                     (- (second position)
                        (* 2 ship-radius))]]
    (svg/group
     {:stroke-width 0.5
      :fill "none"
      :stroke "hotpink"}
     (svg/circle position ship-radius)
     (svg/group
      {:stroke "yellow"}
      (svg/line position heading-pos)))))

(defn app-container []
  (let [ship-state (:ship @state)]
    (svg/svg
     {:width 600
      :height 600}
     (svg/group
      {:transform (g/scale M32 1)}
      (background)
      (ship ship-state)))))

(r/render-component [app-container] (by-id "app"))