(function() {
            const isMobileDevice = () => window.innerWidth <= 768;
            let threeJsActive = false;
            let scene, camera, renderer, mainGroup, ringGroups = [],
                starfield, animationFrameId;
            let mouseX = 0,
                mouseY = 0;
            let targetMouseX = 0,
                targetMouseY = 0;
            const threeContainer = document.getElementById('three-container');
            const phrases = [
                'Fala, -[NOME SEU]-! Beleza',
                'Descubra novos horizontes digitais',
                'Conecte-se ao que realmente importa',
                'Explore. Aprenda. Cresça.',
                'O futuro está a um clique de distância',
                'Navegue por experiências únicas',
                'Inovação que transforma realidades',
                'Seu portal para o mundo online',
                'Descubra o poder da tecnologia',
                'Qual a tarefa de hoje?',
            ];
            const phraseElement = document.getElementById('phrase');
            let phraseIndex = 0;
            let phraseInterval;
            function startPhraseAnimation() {
                phraseElement.textContent = phrases[0];
                phraseElement.classList.remove('fading');
                phraseIndex = 0;
                phraseInterval = setInterval(() => {
                    phraseElement.classList.add('fading');
                    setTimeout(() => {
                        phraseIndex = (phraseIndex + 1) % phrases.length;
                        phraseElement.textContent = phrases[phraseIndex];
                        phraseElement.classList.remove('fading');
                    }, 700);
                }, 4200);
            }
            function stopPhraseAnimation() {
                if (phraseInterval) clearInterval(phraseInterval);
            }
            function createPointSphere(radius, numPoints, colorHex) {
                const geometry = new THREE.BufferGeometry();
                const positions = new Float32Array(numPoints * 3);
                for (let i = 0; i < numPoints; i++) {
                    const u = Math.random() * 2 - 1;
                    const theta = Math.random() * 2 * Math.PI;
                    const r = Math.sqrt(1 - u * u);
                    const x = r * Math.cos(theta) * radius;
                    const y = r * Math.sin(theta) * radius;
                    const z = u * radius;
                    positions[i * 3] = x;
                    positions[i * 3 + 1] = y;
                    positions[i * 3 + 2] = z;
                }
                geometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
                const material = new THREE.PointsMaterial({
                    color: colorHex,
                    size: 0.07,
                    blending: THREE.AdditiveBlending,
                    depthWrite: false,
                    transparent: true,
                    opacity: 0.85,
                });
                return new THREE.Points(geometry, material);
            }
            function createWireframeSphere(radius, segments, colorHex) {
                const geometry = new THREE.SphereGeometry(radius, segments, segments);
                const material = new THREE.MeshBasicMaterial({
                    color: colorHex,
                    wireframe: true,
                    transparent: true,
                    opacity: 0.25,
                    depthWrite: false,
                });
                return new THREE.Mesh(geometry, material);
            }
            function createRing(radius, numPoints, colorHex, tiltX, tiltZ) {
                const geometry = new THREE.BufferGeometry();
                const positions = new Float32Array(numPoints * 3);
                for (let i = 0; i < numPoints; i++) {
                    const angle = (i / numPoints) * Math.PI * 2;
                    const x = Math.cos(angle) * radius;
                    const z = Math.sin(angle) * radius;
                    const y = 0;

                    positions[i * 3] = x;
                    positions[i * 3 + 1] = y;
                    positions[i * 3 + 2] = z;
                }
                geometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
                const material = new THREE.PointsMaterial({
                    color: colorHex,
                    size: 0.04,
                    blending: THREE.AdditiveBlending,
                    depthWrite: false,
                    transparent: true,
                    opacity: 0.65,
                });
                const ring = new THREE.Points(geometry, material);
                ring.rotation.x = tiltX;
                ring.rotation.z = tiltZ;
                return ring;
            }
            function createStarfield(numStars, spread) {
                const geometry = new THREE.BufferGeometry();
                const positions = new Float32Array(numStars * 3);
                for (let i = 0; i < numStars; i++) {
                    positions[i * 3] = (Math.random() - 0.5) * spread;
                    positions[i * 3 + 1] = (Math.random() - 0.5) * spread;
                    positions[i * 3 + 2] = (Math.random() - 0.5) * spread;
                }
                geometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
                const material = new THREE.PointsMaterial({
                    color: 0xaaccff,
                    size: 0.04,
                    blending: THREE.AdditiveBlending,
                    depthWrite: false,
                    transparent: true,
                    opacity: 0.5,
                });
                return new THREE.Points(geometry, material);
            }
            function initThreeJS() {
                if (threeJsActive) return;
                if (typeof THREE === 'undefined') {
                    console.warn('Three.js não está disponível. Usando fallback CSS.');
                    return;
                }
                scene = new THREE.Scene();
                camera = new THREE.PerspectiveCamera(
                    55,
                    window.innerWidth / window.innerHeight,
                    0.1,
                    100
                );
                camera.position.z = 15;
                renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });
                renderer.setSize(window.innerWidth, window.innerHeight);
                renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));
                renderer.setClearColor(0x000000, 0);
                threeContainer.appendChild(renderer.domElement);
                mainGroup = new THREE.Group();
                scene.add(mainGroup);
                const pointSphere = createPointSphere(4.2, 2800, 0x5ba0f0);
                mainGroup.add(pointSphere);
                const wireframe = createWireframeSphere(3.6, 18, 0x2a6090);
                mainGroup.add(wireframe);
                const pointSphereInner = createPointSphere(3.0, 1500, 0x82c4ff);
                mainGroup.add(pointSphereInner);
                const ringDefs = [
                    { radius: 5.6, points: 350, color: 0x7bc8ff, tiltX: 0.35, tiltZ: 0.1, speed: 0.003 },
                    { radius: 6.0, points: 350, color: 0x5da8e8, tiltX: -0.4, tiltZ: 0.6, speed: -0.0025 },
                    { radius: 5.3, points: 300, color: 0x90d0ff, tiltX: 0.7, tiltZ: -0.3, speed: 0.0035 },
                ];
                ringGroups = [];
                ringDefs.forEach(def => {
                    const ringGroup = new THREE.Group();
                    const ring = createRing(def.radius, def.points, def.color, def.tiltX, def.tiltZ);
                    ringGroup.add(ring);
                    ringGroup.userData = { speed: def.speed };
                    mainGroup.add(ringGroup);
                    ringGroups.push(ringGroup);
                });
                starfield = createStarfield(600, 35);
                scene.add(starfield);
                scene.fog = new THREE.FogExp2(0x060d17, 0.00025);
                threeJsActive = true;
                animate();
            }
            function destroyThreeJS() {
                if (!threeJsActive) return;
                if (animationFrameId) {
                    cancelAnimationFrame(animationFrameId);
                    animationFrameId = null;
                }
                if (renderer) {
                    renderer.dispose();
                    if (renderer.domElement && renderer.domElement.parentNode) {
                        renderer.domElement.parentNode.removeChild(renderer.domElement);
                    }
                    renderer = null;
                }
                if (scene) {
                    while (scene.children.length > 0) {
                        const child = scene.children[0];
                        disposeObject(child);
                        scene.remove(child);
                    }
                    scene = null;
                }
                mainGroup = null;
                ringGroups = [];
                starfield = null;
                camera = null;
                threeJsActive = false;
            }
            function disposeObject(obj) {
                if (obj.geometry) obj.geometry.dispose();
                if (obj.material) {
                    if (Array.isArray(obj.material)) {
                        obj.material.forEach(m => m.dispose());
                    } else {
                        obj.material.dispose();
                    }
                }
                while (obj.children && obj.children.length > 0) {
                    const child = obj.children[0];
                    disposeObject(child);
                    obj.remove(child);
                }
            }
            function animate() {
                if (!threeJsActive) return;
                animationFrameId = requestAnimationFrame(animate);
                mouseX += (targetMouseX - mouseX) * 0.04;
                mouseY += (targetMouseY - mouseY) * 0.04;
                if (mainGroup) {
                    mainGroup.rotation.y += 0.002 + mouseX * 0.0008;
                    mainGroup.rotation.x += 0.0008 + mouseY * 0.0005;
                    mainGroup.rotation.z += 0.0003;
                }
                ringGroups.forEach(rg => {
                    rg.rotation.y += rg.userData.speed || 0.003;
                });
                if (starfield) {
                    starfield.rotation.y += 0.0002;
                    starfield.rotation.x += 0.0001;
                }
                if (renderer && camera) {
                    renderer.render(scene, camera);
                }
            }
            function onMouseMove(e) {
                targetMouseX = (e.clientX / window.innerWidth) * 2 - 1;
                targetMouseY = -(e.clientY / window.innerHeight) * 2 + 1;
            }
            function onResize() {
                if (threeJsActive && camera && renderer) {
                    camera.aspect = window.innerWidth / window.innerHeight;
                    camera.updateProjectionMatrix();
                    renderer.setSize(window.innerWidth, window.innerHeight);
                }
                const currentlyMobile = isMobileDevice();
                if (currentlyMobile && threeJsActive) {
                    destroyThreeJS();
                } else if (!currentlyMobile && !threeJsActive) {
                    loadThreeJSAndInit();
                }
            }
            function loadThreeJSAndInit() {
                if (typeof THREE !== 'undefined') {
                    initThreeJS();
                    return;
                }
                const script = document.createElement('script');
                script.src = 'https://cdnjs.cloudflare.com/ajax/libs/three.js/r128/three.min.js';
                script.onload = () => {
                    initThreeJS();
                };
                script.onerror = () => {
                    console.warn('Falha ao carregar Three.js. Usando fundo CSS.');
                };
                document.head.appendChild(script);
            }
            //CARDS DOS SITES!
            const sitesData = [
                { name: 'Google', desc: 'O maior buscador do mundo', url: 'https://www.google.com',
                    icon: 'fa-google', color: '#4285F4' },
                { name: 'YouTube', desc: 'Vídeos para todos os gostos', url: 'https://www.youtube.com',
                    icon: 'fa-youtube', color: '#FF0000' },
                { name: 'GitHub', desc: 'Onde o código ganha vida', url: 'https://www.github.com', icon: 'fa-github',
                    color: '#6e5494' },
                { name: 'LinkedIn', desc: 'Sua rede profissional', url: 'https://www.linkedin.com', icon: 'fa-linkedin-in',
                    color: '#0A66C2' },
                { name: 'Instagram', desc: 'Compartilhe seus momentos', url: 'https://www.instagram.com',
                    icon: 'fa-instagram', color: '#E4405F' },
                { name: 'Wikipedia', desc: 'A enciclopédia livre', url: 'https://www.wikipedia.org',
                    icon: 'fa-wikipedia-w', color: '#1a1a1a' },
            ];
            function buildCards() {
                const grid = document.getElementById('cardsGrid');
                if (!grid) return;
                grid.innerHTML = '';
                sitesData.forEach((site, index) => {
                    const card = document.createElement('a');
                    card.href = site.url;
                    card.target = '_blank';
                    card.rel = 'noopener noreferrer';
                    card.className = 'card';
                    card.style.transitionDelay = `${index * 0.08}s`;
                    card.setAttribute('data-index', index);

                    card.innerHTML = `
                        <div class="card-icon-wrapper" style="background: ${site.color};">
                            <i class="fa-brands ${site.icon}"></i>
                        </div>
                        <h3 class="card-name">${site.name}</h3>
                        <p class="card-desc">${site.desc}</p>
                        <span class="card-btn">Acessar <i class="fas fa-arrow-right" style="margin-left:6px;font-size:0.75rem;"></i></span>
                    `;
                    grid.appendChild(card);
                });
                observeCards();
            }
            function observeCards() {
                const cards = document.querySelectorAll('.card');
                if (!cards.length) return;

                const observer = new IntersectionObserver(
                    (entries) => {
                        entries.forEach(entry => {
                            if (entry.isIntersecting) {
                                entry.target.classList.add('revealed');
                                observer.unobserve(entry.target);
                            }
                        });
                    }, { threshold: 0.2, rootMargin: '0px 0px -40px 0px' }
                );
                cards.forEach(card => observer.observe(card));
            }
            function setup() {
                startPhraseAnimation();
                buildCards();

                if (!isMobileDevice()) {
                    loadThreeJSAndInit();
                } else {
                    threeContainer.innerHTML = '';
                }
                document.addEventListener('mousemove', onMouseMove, { passive: true });
                window.addEventListener('resize', onResize);
                window.addEventListener('orientationchange', () => {
                    setTimeout(onResize, 300);
                });
            }
            function cleanup() {
                stopPhraseAnimation();
                destroyThreeJS();
                document.removeEventListener('mousemove', onMouseMove);
                window.removeEventListener('resize', onResize);
            }
            if (document.readyState === 'loading') {
                document.addEventListener('DOMContentLoaded', setup);
            } else {
                setup();
            }
            window._portalCleanup = cleanup;

            console.log('Portal iniciado!');
            if (isMobileDevice()) {
                console.log('Modo mobile: fundo CSS otimizado ativo.');
            } else {
                console.log('Modo desktop: Three.js carregando...');
            }
        })();