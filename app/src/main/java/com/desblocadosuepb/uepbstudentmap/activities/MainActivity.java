package com.desblocadosuepb.uepbstudentmap.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.fragments.GradeCursoFragment;
import com.desblocadosuepb.uepbstudentmap.fragments.RDMListFragment;

/**
 * Esta classe é a Activity principal da aplicação onde o layout principal
 * é setado e támbém onde os Fragments que representam as principais funções
 * da aplicação são manipulados.
 *
 * @author Eric
 * @version 1
 * @since Release 01 da aplicação
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO Corrigir a cor do texto da Toolbar
        //Setando a Toolbar para substituir a ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recuperar o DrawerLayout do layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_main);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new DrawerItemListener());

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
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.nav_mapa:
                    //TODO: Criar o Fragment do Mapa
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

            //TODO: Revisar o código de FragmentTransitions
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            item.setChecked(true); //Destaca o item do menu de navegação
            setTitle(item.getTitle()); //Troca o título da ActionBar
            drawerLayout.closeDrawers(); //Fecha o Drawer

            return true;
        }
    }
}
