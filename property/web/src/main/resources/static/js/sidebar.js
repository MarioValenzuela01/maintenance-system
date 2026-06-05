document.addEventListener("DOMContentLoaded", function () {
    const menuBtn = document.getElementById("mobileMenuBtn");
    const sidebar = document.getElementById("appSidebar");
    const backdrop = document.getElementById("sidebarBackdrop");

    if (!menuBtn || !sidebar || !backdrop) {
        return;
    }

    function openSidebar() {
        sidebar.classList.add("sidebar-open");
        backdrop.classList.add("show");
        document.body.classList.add("sidebar-mobile-open");
    }

    function closeSidebar() {
        sidebar.classList.remove("sidebar-open");
        backdrop.classList.remove("show");
        document.body.classList.remove("sidebar-mobile-open");
    }

    menuBtn.addEventListener("click", function () {
        if (sidebar.classList.contains("sidebar-open")) {
            closeSidebar();
        } else {
            openSidebar();
        }
    });

    backdrop.addEventListener("click", closeSidebar);

    document.addEventListener("keydown", function (event) {
        if (event.key === "Escape") {
            closeSidebar();
        }
    });

    sidebar.querySelectorAll("a").forEach(function (link) {
        link.addEventListener("click", function () {
            if (window.innerWidth <= 992) {
                closeSidebar();
            }
        });
    });
});