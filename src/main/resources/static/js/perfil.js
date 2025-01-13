function toggleMenu() {
    const menu = document.querySelector('.menu');
    menu.style.display = (menu.style.display === 'block' || menu.style.display === '') ? 'none' : 'block';
  }
  
  // Cerrar el menú si se hace clic fuera de él
  document.addEventListener('click', function(event) {
    const menu = document.querySelector('.menu');
    const menuIcon = document.querySelector('.menu-icon');
    
    if (event.target !== menu && !menu.contains(event.target) && event.target !== menuIcon) {
      menu.style.display = 'none';
    }
  });
  