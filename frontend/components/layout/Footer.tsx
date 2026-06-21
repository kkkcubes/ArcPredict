export default function Footer() {
  return (
    <footer
      className="
        border-t
        border-gray-800
        mt-12
      "
    >
      <div
        className="
          container
          py-6
          flex
          justify-between
          items-center
        "
      >
        <p
          className="
            text-gray-400
          "
        >
          © 2026 ArcPredict
        </p>

        <p
          className="
            text-gray-500
          "
        >
          Built on Arc Network
        </p>
      </div>
    </footer>
  );
}