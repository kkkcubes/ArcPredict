"use client";

import { useEffect, useState } from "react";

export function useActiveSection() {
  const [activeSection, setActiveSection] =
    useState("#top");

  useEffect(() => {

    const sections = [
      "#top",
      "#verification",
      "#markets",
      "#portfolio",
      "#analytics",
      "#leaderboard",
    ];

    const observer =
      new IntersectionObserver(

        (entries) => {

          entries.forEach((entry) => {

            if (entry.isIntersecting) {

              setActiveSection(
                `#${entry.target.id}`
              );

            }

          });

        },

        {
          root: null,
          rootMargin: "-20% 0px -60% 0px",
          threshold: 0.2,
        }

      );

    sections.forEach((id) => {

      const element =
        document.querySelector(id);

      if (element) {
        observer.observe(element);
      }

    });

    return () => {

      observer.disconnect();

    };

  }, []);

  return {
    activeSection,
  };
}