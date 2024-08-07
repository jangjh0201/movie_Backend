import { showWishModal } from "../common/modal.js";
import { deleteWish, getWishes } from "../common/api.js";

document.addEventListener("DOMContentLoaded", function () {
  getWishes()
    .then((data) => {
      renderWishes(data);
    })
    .catch((error) => console.error("Error:", error));
});

function renderWishes(wishes) {
  const container = document.getElementById("wishList");
  container.innerHTML = ""; // Clear previous results

  if (wishes.length === 0) {
    container.innerHTML =
      "<p class='text-center no-movies-message'>저장된 영화가 없습니다.</p>";
    return;
  }

  const table = document.createElement("table");
  table.classList.add("table", "table-hover", "table-bordered");

  const thead = document.createElement("thead");
  thead.innerHTML = `
    <tr>
      <th scope="col">제목</th>
      <th scope="col">개봉</th>
      <th scope="col">장르</th>
      <th scope="col">제작국가</th>
      <th scope="col">상영시간(분)</th>
      <th scope="col">감독</th>
      <th scope="col">배우</th>
      <th scope="col"></th>
    </tr>`;
  table.appendChild(thead);

  const tbody = document.createElement("tbody");
  tbody.classList.add("table-group-divider");

  wishes.forEach((wish) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td><a href="#" class="wish-title" data-wish='${encodeURIComponent(
        JSON.stringify(wish)
      )}'>${wish.title}</a></td>
      <td>${wish.prodYear}</td>
      <td>${wish.genre}</td>
      <td>${wish.nation}</td>
      <td>${wish.runtime}</td>
      <td>${wish.director}</td>
      <td class="fixed-width-td">${wish.actor}</td>
      <td>
        <button class="btn btn-outline-primary delete-button" data-wish-id="${
          wish.id
        }">삭제</button>
      </td>`;
    tbody.appendChild(tr);
  });

  table.appendChild(tbody);
  container.appendChild(table);

  // Delete button event listener
  document.querySelectorAll(".delete-button").forEach((button) => {
    button.addEventListener("click", function () {
      deleteWish(this.dataset.wishId);
    });
  });

  // Wish title click event listener for modal
  document.querySelectorAll(".wish-title").forEach((title) => {
    title.addEventListener("click", function (event) {
      event.preventDefault();
      showWishModal(JSON.parse(decodeURIComponent(this.dataset.wish)));
    });
  });
}
