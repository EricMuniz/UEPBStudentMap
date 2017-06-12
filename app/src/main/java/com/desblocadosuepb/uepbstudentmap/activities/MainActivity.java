package com.desblocadosuepb.uepbstudentmap.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.fragments.GradeCursoFragment;
import com.desblocadosuepb.uepbstudentmap.fragments.MapaFragment;
import com.desblocadosuepb.uepbstudentmap.fragments.NovoRDMDialogFragment;
import com.desblocadosuepb.uepbstudentmap.fragments.RDMListFragment;

/**
 * Esta classe é a Activity principal da aplicação onde o layout principal
 * é setado e támbém onde os Fragments que representam as principais funções
 * da aplicação são manipulados.
 *
 * @author Eric
 * @version 1.2
 * @since Release 01 da aplicação
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define a Toolbar para substituir a ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Define o FAB que adiciona um novo RDM
        FloatingActionButton floatingButton = (FloatingActionButton) findViewById(R.id.action_add);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Quando clickado, o FAB abre um DialogFragment responsável por criar o RDM
                FragmentManager fragmentManager = getSupportFragmentManager();
                NovoRDMDialogFragment novoRDMDialogFragment = new NovoRDMDialogFragment();
                novoRDMDialogFragment.show(fragmentManager, "Novo RDM");
            }
        });

        //Recupera o DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_main);
        //Define o Navigation Drawer Toggle (o ícone de três linhas da barra de ação)
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        //Recupera a NavigationView e define
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new DrawerItemListener());

        //Transaction que define o Fragment que aparece quando o app é aberto
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, new MapaFragment(), "visible_fragment");
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();

        //Listener do BackStack dos Fragments
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Fragment fragment = fragmentManager.findFragmentByTag("visible_fragment");
                        if(fragment instanceof MapaFragment){
                            getSupportActionBar().setTitle(R.string.menu_mapa_campus);
                            navigationView.setCheckedItem(R.id.nav_mapa);
                        }
                        if(fragment instanceof RDMListFragment){
                            getSupportActionBar().setTitle(R.string.menu_meus_horarios);
                            navigationView.setCheckedItem(R.id.nav_horario);
                        }
                        if(fragment instanceof GradeCursoFragment){
                            getSupportActionBar().setTitle(R.string.menu_horario_curso);
                            navigationView.setCheckedItem(R.id.nav_horario_curso);
                        }
                        //ToDo: adicionar o fragment da ajuda
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        //O botão de "home" da ActionBar deve abrir e fechar o Drawer
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        //Sincroniza o Toggle caso a tela seja rotacionada e a Activity recriada
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        //Passa qualquer mudança de configuração para o Drawer Toggle
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Classe interna da Activity principal da aplicação.
     * Esta classe define um Listener para os itens do NavigationDrawer.
     * Quando um item é clicado, é esta classe quem realiza a transição dos Fragments
     * na tela.
     */
    private class DrawerItemListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.nav_mapa:
                    //TODO: Melhorar o Fragment do Mapa
                    fragment = new MapaFragment();
                    break;
                case R.id.nav_horario:
                    fragment = new RDMListFragment();
                    break;
                case R.id.nav_horario_curso:
                    fragment = new GradeCursoFragment();
                    break;
                case R.id.nav_help:
                    //TODO: Criar o Fragment da Ajuda
                    break;
                default:
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, fragment, "visible_fragment");
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();

            setTitle(item.getTitle()); //Troca o título da ActionBar
            drawerLayout.closeDrawer(GravityCompat.START); //Fecha o Drawer

            return true;
        }
    }
}
