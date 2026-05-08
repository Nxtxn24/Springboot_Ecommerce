import { useState } from "react";

export default function StarRating({
  rating = 0,
  onRate,
  size = 24,
  readonly = false
}) {
  const [hoverValue, setHoverValue] = useState(null);

  const getDisplayValue = () => {
    return hoverValue !== null ? hoverValue : rating;
  };

  const display = getDisplayValue();

  const handleClick = (value) => {
    if (!readonly && onRate) {
      onRate(value);
    }
  };

  return (
    <div style={{ display: "flex", gap: 4, cursor: readonly ? "default" : "pointer" }}>
      {[1, 2, 3, 4, 5].map((star) => {
        const filled = display >= star;
        const halfFilled = display >= star - 0.5 && display < star;

        return (
          <span
            key={star}
            style={{
              fontSize: size,
              color: filled || halfFilled ? "#f5b301" : "#ccc",
              position: "relative",
              userSelect: "none"
            }}
            onMouseEnter={() => !readonly && setHoverValue(star)}
            onMouseLeave={() => !readonly && setHoverValue(null)}
            onClick={() => handleClick(star)}
          >
            {/* Full star */}
            {filled && "★"}

            {/* Half star */}
            {!filled && halfFilled && (
              <span style={{ position: "relative" }}>
                <span style={{ color: "#f5b301", position: "absolute", width: "50%", overflow: "hidden" }}>
                  ★
                </span>
                <span style={{ color: "#ccc" }}>★</span>
              </span>
            )}

            {/* Empty star */}
            {!filled && !halfFilled && "★"}
          </span>
        );
      })}
    </div>
  );
}