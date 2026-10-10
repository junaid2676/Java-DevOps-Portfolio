(function () {
    'use strict';

    const navbar = document.getElementById('navbar');
    const navToggle = document.getElementById('navToggle');
    const navMenu = document.getElementById('navMenu');
    const navLinks = document.querySelectorAll('.nav-link');
    const typedTextEl = document.getElementById('typedText');

    const roles = [
        'DevOps Engineer',
        'Cloud Enthusiast',
        'CI/CD Pipeline Builder',
        'Computer Science Student',
        'Kubernetes Explorer'
    ];

    let roleIndex = 0;
    let charIndex = 0;
    let isDeleting = false;

    function typeEffect() {
        const current = roles[roleIndex];

        if (isDeleting) {
            typedTextEl.textContent = current.substring(0, charIndex--);
        } else {
            typedTextEl.textContent = current.substring(0, charIndex++);
        }

        let delay = isDeleting ? 50 : 100;

        if (!isDeleting && charIndex === current.length + 1) {
            delay = 2000;
            isDeleting = true;
        } else if (isDeleting && charIndex === 0) {
            isDeleting = false;
            roleIndex = (roleIndex + 1) % roles.length;
            delay = 300;
        }

        setTimeout(typeEffect, delay);
    }

    typeEffect();

    window.addEventListener('scroll', function () {
        if (window.scrollY > 20) {
            navbar.classList.add('scrolled');
        } else {
            navbar.classList.remove('scrolled');
        }

        updateActiveNav();
    });

    navToggle.addEventListener('click', function () {
        navToggle.classList.toggle('active');
        navMenu.classList.toggle('active');
    });

    navLinks.forEach(function (link) {
        link.addEventListener('click', function () {
            navToggle.classList.remove('active');
            navMenu.classList.remove('active');
        });
    });

    function updateActiveNav() {
        const sections = document.querySelectorAll('section[id]');
        const scrollY = window.scrollY + 100;

        sections.forEach(function (section) {
            const top = section.offsetTop;
            const height = section.offsetHeight;
            const id = section.getAttribute('id');

            if (scrollY >= top && scrollY < top + height) {
                navLinks.forEach(function (link) {
                    link.classList.remove('active');
                    if (link.getAttribute('href') === '#' + id) {
                        link.classList.add('active');
                    }
                });
            }
        });
    }

    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
            }
        });
    }, observerOptions);

    document.querySelectorAll('.section-header, .skill-category, .project-card, .timeline-item, .edu-card, .about-text, .about-card, .contact-info, .contact-form').forEach(function (el) {
        el.classList.add('fade-in');
        observer.observe(el);
    });

    const contactForm = document.getElementById('contactForm');
    const formStatus = document.getElementById('formStatus');

    contactForm.addEventListener('submit', async function (e) {
        e.preventDefault();

        const submitBtn = contactForm.querySelector('button[type="submit"]');
        submitBtn.disabled = true;
        submitBtn.textContent = 'Sending...';
        formStatus.textContent = '';
        formStatus.className = 'form-status';

        const formData = new FormData(contactForm);
        const params = new URLSearchParams();
        for (const pair of formData) {
            params.append(pair[0], pair[1]);
        }

        try {
            const response = await fetch('/api/contact', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: params.toString()
            });

            const result = await response.json();

            if (result.success) {
                formStatus.textContent = result.message;
                formStatus.classList.add('success');
                contactForm.reset();
            } else {
                formStatus.textContent = result.error || 'Something went wrong. Please try again.';
                formStatus.classList.add('error');
            }
        } catch (err) {
            formStatus.textContent = 'Network error. Please try again later.';
            formStatus.classList.add('error');
        } finally {
            submitBtn.disabled = false;
            submitBtn.textContent = 'Send Message';
        }
    });
})();
